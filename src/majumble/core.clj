(ns majumble.core
  "A postfix, quick and dirty martix manipulation language."
  (:require [instaparse.core :as insta]
            [clojure.core.matrix :as ma]))

(ma/set-current-implementation :vectorz)

(def source->tree
  "A function to parse majumble code."
  (insta/parser "S = VS | OE
                 <VS> = (V <W>)* V
                 <V> = M | NS
                 M = <'['> (NS | (M <W>?)+) <']'>
                 NS = (N <W>)* N <W?>
                 <N> = #'[0-9]+'
                 AS = (OE <W>)? VS <W>
                 OE = AS O
                 O = '+' | '*' | '/' | '-' | \"'\" | 'x' | '.'
                 W = #'\\s+'"))
