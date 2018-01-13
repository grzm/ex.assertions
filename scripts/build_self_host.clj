(require '[cljs.build.api]
         '[clojure.java.io :as io])

(def output-dir "target/out/self-host/")

(cljs.build.api/build "test/self-host/src"
  {:main       'com.grzm.ex.assertions.self-host-test-runner
   :output-to  (str output-dir "main.js")
   :output-dir output-dir
   :target     :nodejs})

(def source-files
  #{"cljs/test.cljc"
    "cljs/analyzer/api.cljc"
    "cljs/reader.clj"
    "clojure/template.clj"})

(defn copy-source
  [filename]
  (spit (str output-dir filename)
    (slurp (io/resource filename))))

(dorun (map copy-source source-files))
