(ns speclj.shoulds.matrix
  (:require (speclj [core :refer [-to-s -fail]]
                    [platform :refer [new-exception endl]])
            [clojure.core.matrix.operators :as mo]))

(defmacro should-matrix==
  [expected-form actual-form]
  `(let [expected# ~expected-form
         actual# ~actual-form
         fail-message# (str "Expected: " (-to-s expected#) endl
                            "     got: " (-to-s actual#)
                            " (using matrix ==)")]
     (try
       (when-not (mo/== expected# actual#) (-fail fail-message#))
       (catch java.lang.RuntimeException ex#
         (let [message# (str fail-message# endl
                             "Cause: "(.getMessage ex#))
               ex-stacktrace# (.getStackTrace ex#)]
           (throw
             (doto (new-exception message#)
               (.initCause ex#)
               (.setStackTrace ex-stacktrace#))))))))
