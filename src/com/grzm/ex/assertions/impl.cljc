(ns com.grzm.ex.assertions.impl)

(defn thrown-with-data?
  [msg [_ data-fn & body :as form] klass]
  `(try
     ~@body
     ;; We expect body to throw. If we get here, it's a failure
     {:type     :fail
      :message  (str (when ~msg (str ~msg ": "))
                     "expected exception")
      :expected '~form
      :actual   nil}
     (catch ~klass e#
       (let [d# (ex-data e#)]
         (if (~data-fn d#)
           {:type     :pass
            :message  ~msg
            :expected '~form
            :actual   e#}
           {:type     :fail
            :message  (str (when ~msg (str ~msg ": "))
                           "ex-data doesn't match")
            :expected '~data-fn
            :actual   d#})))))
