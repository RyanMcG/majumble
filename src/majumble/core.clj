(ns majumble.core
  "A postfix, quick and dirty martix manipulation language."
  (:require [instaparse.core :as insta]
            [clojure.core.matrix :as ma]))

(ma/set-current-implementation :vectorz)

(def source->tree
  "A function to parse majumble code."
  (insta/parser "S = VS | OE
                 <VS> = (V <W>?)* V
                 <V> = M | NS
                 M = <'['> (NS | (M <W>?)+) <']'>
                 NS = (N <W>)* N <W?>
                 <N> = #'[0-9]+'
                 AS = (OE <W>)? VS <W>?
                 OE = AS O
                 O = '+' | '*' | '/' | '-' | \"'\" | 'x' | '.'
                 W = #'\\s+'"))

(def lookup-operator
  "A mapping of strings to the internal operations they represent."
  {"+" ma/add
   "*" ma/mul
   "x" ma/cross
   "." ma/det
   "/" ma/div
   "-" ma/sub
   "'" ma/transpose})

(defn operate
  "Applies the operator to the given arguments."
  [args operator]
  (apply operator args))

(defn M->matrix
  "Take arguments to an M expression and coerce it to a vector."
  [& matricies]
  (ma/matrix (let [first-matrix (first matricies)]
               (vec (if (seq? first-matrix)
                      first-matrix
                      matricies)))))

(defn list-if-not-seq
  [& results]
  (let [first-result (first results)]
    (if (seq? first-result) first-result results)))

(def ^:const ^:private transformations-spec
  "A mapping of terminals to transformation functions."
  {:NS (comp (partial map read-string) list)
   :S list-if-not-seq
   :O lookup-operator
   :OE operate
   :AS list-if-not-seq
   :M M->matrix})

(def transform (partial insta/transform transformations-spec))

(def parse
  "Parse and transform the given majumble code."
  (comp transform source->tree))
