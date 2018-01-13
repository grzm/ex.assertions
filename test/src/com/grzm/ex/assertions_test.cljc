(ns com.grzm.ex.assertions-test
  (:require
   #?(:clj [clojure.test :refer [deftest is]]
      :cljs [cljs.test :refer-macros [deftest is]])
   [com.grzm.ex.assertions]))

(deftest test-thrown-with-data
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::self-inflicted (:cause %)) ;; function to match ex-data
        ;; and something that throws ExceptionInfo
        (throw (ex-info "An error was thrown" {:cause ::self-inflicted})))))

(deftest test-thrown-with-data-no-throw
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::nope (:cause %))
        (constantly true))))

(deftest test-thrown-with-data-fail
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::nope (:cause %))
        (throw (ex-info "An error was thrown" {:cause ::self-inflicted})))))

(deftest test-thrown-with-data-non-ex-info
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::nope (:cause %))
        (throw #?(:clj (Exception. "I'm just a plain Exception")
                  :cljs (js/Error. "I'm just a plain Error"))))))
