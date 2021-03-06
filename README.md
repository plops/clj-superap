# CljSuperApp

hier gibt es ein tutorial wie man mit clojure instantan die gui  und funktionen in einer app aendern kann:
https://github.com/alexander-yakushev/events/blob/master/tutorial.md

so bin ich entwicklung in common lisp gewoehnt.

ich installiere die app mit adb:
```
adb -d install -r clj-superap.apk
```
mit
```adb logcat |grep 9999```
sollte man die folgende zeile sehen
```I/neko.tools.repl(15264): Nrepl started at port 9999```

es ist uebrigens nicht notwendig das usb kabel verbunden zu lassen.
mit folgenden befehlen laesst sich der adb service auch ueber wlan uebertragen.
```
adb tcpip 5555
adb connect 192.168.0.105:5555
```
auf http://stackoverflow.com/questions/2604727/how-can-i-connect-to-android-with-adb-over-tcp wird das genauer beschrieben.

mit diesem port kann man sich von emacs den cider-mode verbinden.

(fall nicht vorhanden muss der mode in emacs mit M-x package-install cider installiert werden)

den port muss man mit
```
adb forward tcp:9999  tcp:9999
```
auf den lokalen rechner umleiten.


dann in emacs ausfuehren
```
M-x cider-connect <enter>
localhost <enter>
9999 <enter>
```
in dem neuen fenster kann man dann code ausfuehren:
```
user> (+ 1 2)
3
```
dann in emacs diese datei aufmachen
https://github.com/plops/clj-superap/blob/master/src/clojure/eu/aeiounce/clj_superapp/main.clj

auf der s-expression
(ns eu.aeiounce.clj_superapp.main ...

C-M-x druecken um sie auszufuehren

und dann
 in der vorletzten zeile hier hin gehen:

```
:on-click (fn [_] (notify-from-edit (*a)))}]]))    ;; run C-x C-e here to update
                                                ^
```

und dort C-x C-e aufrufen. das fuehrt den on-ui befehl neu aus.
wenn man z.b. den string :hint "Type tjkext here" aendert und dann on-ui neu ausfuehrt sieht man diese aenderung auf dem android

jetzt muss ich nur noch rausfinden wie man ein opengl fenster aufmacht.



## Comilation

https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein von leiningen.org runterladen, in PATH installieren und dann "lein droid doall" aufrufen

die applikation ist groesstenteil automatisch von lein droid erzeugt wurden. in project.clj habe ich den android sdk pfad und die android platform version setzen muessen.

leider liefert android ein paar programme nur in 32bit versionen aus und ich musste auf die harte weise lernen, dass dieses programm in einem  virtuellen server mit 1GB ram nicht uebersetzt werden kann. genauer gesagt bricht "dx" aus speichermangel ab.

## Resources

Clojure on Android - Alexander Yakushev

https://www.youtube.com/watch?v=mVXTcAEKgF8

lein-droid  compiles the source into apk

boot droid  improvement of  lein-droid for multi target projects

neko   wrapper for android api, dynamic compilation, repl, cider
       can expand into xml ui declarations

```
=  neko.ui/get-screen-orientation landscape


(require '[neko.find-view :refer [find-view]])
(find-view activity ::name)
; modify property
(config (find-view activity ::send) :enabled :false) 
```

in android you have to remember types for sql access
```
neko.data.sqlite :as db

db-schema
db/make-schema :tables {:employees {:columns {:_id "int primary key"
	       	       		   	      :name "text not null" ...

get-db-helper
(memoize  (fn [] (db/create-helper db-schema)))


get-db
(db/get-database (get-db-helper) :write)

(db/insert (get-db) :employees {:name "Max Mustermann"})

;; sending someone on vacation
(db/update ...


(db/query-seq ..
```

shared preferences to preserve data between restarts of app

```
(defpreferences p "pref_file")

(swap! p assoc :last-user "hans")

;; restart at some point
(:last-user @p) ;; => joe

```
neko.dialog.alert
```
(-> (alert-dialog-builder {:message "bla"})
    .create
    .show)
```

neko simplifies
activities, action bars, menus, intents, resources, data adapters, notifications

skummet optimizing clojure compiler

most of loading time from initializing vars

unused functions can't be deleted (proguard java treeshaker works with
output of skummet)

debugging in cider doesn't work yet
but inspection

demo
```
(on-ui neko.notify/toast "Hello world")

(def data (atom ["1" "2" "3"]))

(on-ui (set-content-view! (*a)
       [:list-view {:adapter (ref-adapter
       		   (lambda [_]
		   	   [:text-view])
		(lamdba [_ view __ elem]
			(config view :text (str elem)))
		data)}]))

(swap! data conj "bla")

(def github-get
     (memoize (lambda [req]
     	(json/read-str (slurp (str "https://api.github.com" req))))))

(reset! data (github-get "/orgs/clojure/repos"))

;; nice example how to parse the data and display it 
```

lein with-profile lean droid doall


## License

keine ahnung 

Copyright © 2015 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
