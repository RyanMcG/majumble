(ns speclj.shoulds.matrix
  (:require (speclj [core :refer [-to-s -fail]]
                    [platform :refer [endl]])
            [clojure.core.matrix.operators :as mo]))

(defmacro should-matrix==
  [expected-form actual-form]
  `(let [expected# ~expected-form
         actual# ~actual-form]
     (if-not (mo/== expected# actual#)
       (-fail (str "Expected: " (-to-s expected#) endl
                   "     got: " (-to-s actual#) " (using matrix ==)")))))
