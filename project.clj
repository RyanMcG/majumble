(defproject majumble "0.1.0-SNAPSHOT"
  :description "A j-like language for matrix manipulation using vectorz-clj."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [net.mikera/vectorz-clj "0.10.0"]
                 [instaparse "1.2.0"]]
  :profiles {:dev {:dependencies [[speclj "2.7.4"]]}}
  :plugins [[speclj "2.7.4"]]
  :test-paths ["spec"]
  :main majumble.core)
