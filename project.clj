(defproject clj-superap/clj-superap "0.1.0-SNAPSHOT"
  :description "FIXME: Android project description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :global-vars {*warn-on-reflection* true}

  :source-paths ["src/clojure" "src"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :plugins [[lein-droid "0.4.3"]]

  :dependencies [[org.clojure-android/clojure "1.7.0-r2"]
                 [neko/neko "4.0.0-alpha5"]]
  :profiles {:default [:dev]

             :dev
             [:android-common :android-user
              {:dependencies [[org.clojure/tools.nrepl "0.2.10"]
	      		     [cider/cider-nrepl "0.9.1"]]
               :target-path "target/debug"
	                      :android {:aot :all-with-unused
	       
                         :rename-manifest-package "eu.aeiounce.clj_superapp.debug"
                         :manifest-options {:app-name "CljSuperApp (debug)"}}}]
             :release
             [:android-common
              {:target-path "target/release"
               :android
               {:keystore-path "/home/martin/.android/private.keystore"
                :key-alias "AndroidTestKey"
                ;; :sigalg "MD5withRSA"
 		
 	                :ignore-log-priority [:debug :verbose]
                :aot :all
                :build-type :release}}]}

  :android {;; Specify the path to the Android SDK directory.
            ;; :sdk-path "/home/user/path/to/android-sdk/"
	    :sdk-path "/home/martin/android/android-sdk-linux"
            ;; Try increasing this value if dexer fails with
            ;; OutOfMemoryException. Set the value according to your
            ;; available RAM.
            :dex-opts ["-JXmx1000M" "--incremental"]

            :target-version "16"
            :aot-exclude-ns ["clojure.parallel" "clojure.core.reducers"
                             "cider.nrepl" "cider-nrepl.plugin"
                             "cider.nrepl.middleware.util.java.parser"
                             #"cljs-tooling\..+"]})
