(defproject majumble "0.1.0"
  :description "A postfix language for matrix manipulation using vectorz-clj."
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [net.mikera/vectorz-clj "0.22.0"]
                 [instaparse "1.3.2"]]
  :profiles {:dev {:dependencies [[speclj "2.7.4"]
                                  [speclj.shoulds.matrix "0.1.0-SNAPSHOT"]]}}
  :plugins [[speclj "2.7.4"]]
  :test-paths ["spec"]
  :main majumble.core)
