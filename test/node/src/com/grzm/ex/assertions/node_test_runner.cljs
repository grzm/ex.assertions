(ns com.grzm.ex.assertions.node-test-runner
  (:require
   [cljs.nodejs :as nodejs]
   [cljs.test :as test :refer-macros [run-tests]]
   [com.grzm.ex.assertions-test]))

(nodejs/enable-util-print!)

(defn -main []
  (run-tests
    'com.grzm.ex.assertions-test))

(set! *main-cli-fn* -main)
