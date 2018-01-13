# A portable clojure.test assertion example

This is an example of extending clojure.test's `assert-expr` that
works in Clojure and ClojureScript, both JVM-compiled and self-hosted.

A more detailed description of the implementation is available at
[seespotcode.net][seespotcode-post].

[seespotcode-post]: http://seespotcode.net/2018/01/13/portable-clojure-test-assert-expr/

## Usage

The example provides the `com.grzm.ex.assertions/thrown-with-data?`
assertion that tests whether a thrown exception includes the
expected `ExceptionInfo` data.

```clojure
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

;; Passes
```

Custom message depending on failure mode. Here the actual case doesn't throw.

```clojure
(deftest test-thrown-with-data-no-throw
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::nope (:cause %))
        (constantly true))))

;; Fail in test-thrown-with-data-no-throw
;; expected exception
;; expected: (com.grzm.ex.assertions/thrown-with-data?
;;  (fn*
;;   [p1__14278#]
;;   (= :com.grzm.ex.assertions-test/nope (:cause p1__14278#)))
;;  (constantly true))
;; actual: nil
```

Here, the actual `ExceptionInfo` data doesn't match expected. The actual
data is displayed.

```clojure
(deftest test-thrown-with-data-fail
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::nope (:cause %))
        (throw (ex-info "An error was thrown" {:cause ::self-inflicted})))))

;; Fail in test-thrown-with-data-fail
;; ex-data doesn't match
;; expected: (fn*
;;  [p1__14287#]
;;  (= :com.grzm.ex.assertions-test/nope (:cause p1__14287#)))
;;  actual: {:cause :com.grzm.ex.assertions-test/self-inflicted}
```

And when something other than `ExceptionInfo` is thrown, fall back to
the default handling.

```clojure
(deftest test-thrown-with-data-non-ex-info
  (is (com.grzm.ex.assertions/thrown-with-data?
        #(= ::nope (:cause %))
        (throw #?(:clj (Exception. "I'm just a plain Exception")
                  :cljs (js/Error. "I'm just a plain Error"))))))
;; Error in test-thrown-with-data-non-ex-info
;; expected: (com.grzm.ex.assertions/thrown-with-data?
;;  (fn*
;;   [p1__14296#]
;;   (= :com.grzm.ex.assertions-test/nope (:cause p1__14296#)))
;;  (throw (Exception. "I'm just a plain Exception")))
;;    error: java.lang.Exception: I'm just a plain Exception
```

## Testing

Errors and failures are expected, as we're testing the failure modes
as well. (Yes, we could alternatively write tests to test the test
output, but that's beyond the scope of this example.)

Testing the Clojure implementation:

    lein test


Testing the JVM-compiled ClojureScript implementation:

    lein cljsbuild once

Testing the implementation in self-hosted ClojureScript:

    scripts/test-self-host

## Background

This is a distillation of work done to improve error reporting in
[test.check][test.check:assert-expr]. Thanks Gary Fredericks and Mike
Fikes for their help and examples!

[test.check]: https://github.com/clojure/test.check

[test.check:assert-expr]: https://github.com/clojure/test.check/blob/master/src/main/clojure/clojure/test/check/clojure_test/assertions.cljc

## License

© 2018 Michael Glaesemann

Released under the MIT License. See LICENSE for details.
