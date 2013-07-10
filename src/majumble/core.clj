(ns majumble.core
  (:require [instaparse.core :as insta]
            [clojure.core.matrix :as ma]
            [clojure.core.matrix.operators :as mo]))

(ma/set-current-implementation :vectorz)

(def parse
  "A function to parse majumble code."
  (insta/parser "S = VS OE*
                 <VS> = (V <W>)* V <W?>
                 <V> = M | NS
                 M = <'['> (NS | (M <W>?)+) <']'>
                 NS = (N <W>)* N <W?>
                 <N> = #'[0-9]+'
                 OE = O <W> VS
                 O = '+' | '*' | '/' | '-' | \"'\"
                 W = #'\\s+'"))
