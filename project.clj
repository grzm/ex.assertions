(defproject com.grzm/ex.assertions "0.1.0"
  :description "A portable clojure.test assertion example"
  :url "https://github.com/grzm/ex.assertions"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}
  :profiles {:dev
             {:dependencies [[org.clojure/clojure "1.9.0"]
                             [org.clojure/clojurescript "1.9.946"]]}

             :self-host
             {:dependencies [[org.clojure/clojure "1.9.0"]
                             [org.clojure/clojurescript "1.9.946"]]
              :main         clojure.main}}
  :plugins [[lein-cljsbuild "1.1.5"]]
  :source-paths ^:replace ["src"]
  :test-paths ^:replace ["test/src"]
  :cljsbuild {:builds
              [{:id             "node-dev"
                :source-paths   ["src" "test/src" "test/node/src"]
                :notify-command ["node" "test/node/dev_runner.js"]
                :compiler       {:optimizations :none
                                 :main          com.grzm.ex.assertions.node-test-runner
                                 :static-fns    true
                                 :target        :nodejs
                                 :output-to     "target/cljs/node_dev/tests.js"
                                 :output-dir    "target/cljs/node_dev/out"
                                 :source-map    true}}]})
