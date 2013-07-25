(ns majumble.core
  "A quick and dirty matrix manipulation postfix language."
  (:require [instaparse.core :as insta]
            [clojure.edn :as edn]
            [clojure.core.matrix :as ma]))

(ma/set-current-implementation :vectorz)

(def source->tree
  "A function to parse majumble code."
  (insta/parser "S = VS | OE
                 <VS> = <W>? (V <W>?)+ (* values *)
                 <V> = M | N | CE (* value *)
                 M = <'['> VS <']'> (* matrix *)
                 N = #'(\\.[0-9]+|[0-9]+\\.?[0-9]*)'
                 CE = '(' #'.'+ ')'  (* clojure expression *)
                 OE =  AS O (* operator expression *)
                 AS =  OE <W>? VS? | VS (* arguments *)
                 O = '+' | '*' | '/' | '-' | \"'\" | 'x' | '.' (* operator *)
                 W = #'[,\\s]+' (* whitespace *)"))

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
  {:N edn/read-string
   :CE (comp eval read-string (partial apply str))
   :S list-if-not-seq
   :O lookup-operator
   :OE operate
   :AS list-if-not-seq
   :M M->matrix})

(def transform (partial insta/transform transformations-spec))

(def parse
  "Parse and transform the given majumble code."
  (comp transform source->tree))
