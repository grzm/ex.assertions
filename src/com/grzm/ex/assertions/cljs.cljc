(ns com.grzm.ex.assertions.cljs
  (:require
   [cljs.test :as test]
   [com.grzm.ex.assertions.impl :as impl]))

#?(:clj
   (defmethod test/assert-expr 'com.grzm.ex.assertions/thrown-with-data?
     [_env msg form] ;; has 3 arguments instead of 2
     `(test/do-report
        ~(impl/thrown-with-data? msg form 'cljs.core/ExceptionInfo))))
