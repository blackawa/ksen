(defproject ksen "0.1.3-SNAPSHOT"
  :description "Parse tables drawn with box-drawing characters"
  :url "http://github.com/blackawa/ksen"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns ksen.core}
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.3.1"]
                                  [eftest "0.5.9"]]
                   :source-paths ["dev/src" "test"]
                   :repl-options {:init-ns user}}})
