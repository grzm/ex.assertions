(ns com.grzm.ex.assertions
  #?(:cljs (:require-macros [com.grzm.ex.assertions.cljs]))
  (:require
   #?(:clj [clojure.test :as test]
      :cljs [cljs.test :as test])
   [com.grzm.ex.assertions.impl :as impl]))

#?(:clj
   (defmethod test/assert-expr 'com.grzm.ex.assertions/thrown-with-data?
     [msg [_ data-fn & body :as form]]
     `(test/do-report
        ~(impl/thrown-with-data? msg form 'clojure.lang.ExceptionInfo)))
   :cljs
   (when (exists? js/cljs.test$macros)
     (defmethod js/cljs.test$macros.assert_expr 'com.grzm.ex.assertions/thrown-with-data?
       [_env msg form]
       `(test/do-report
          ~(impl/thrown-with-data? msg form 'cljs.core/ExceptionInfo)))))
