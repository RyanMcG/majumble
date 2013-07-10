(ns majumble.core-spec
  (:require [speclj.core :refer :all]
            [majumble.core :refer :all]))

(describe "creating parse-trees"
  (it "parses without operators"
    (should= [:S [:NS "10" "9"]]
             (source->tree "10 9")))
  (it "parses number arguments"
    (should= [:S [:OE [:OE [:NS "10" "9"] [:O "+"]] [:NS "3"] [:O "*"]]]
             (source->tree "10 9 + 3 *")))
  (it "parses matrix arguments"
    (should= [:S
              [:OE
               [:OE [:M [:NS "10" "9"]] [:M [:NS "8" "7"]] [:O "+"]]
               [:M [:NS "3" "4"]] [:O "x"]]]
             (source->tree "[10 9] [8 7] + [3 4] x")))
  (it "parses 2-D matrix arguments"
    (should= [:S
              [:OE
               [:M [:M [:NS "1" "2"]] [:M [:NS "3" "4"]]]
               [:M [:M [:NS "5" "6"]] [:M [:NS "7" "8"]]] [:O "+"]]]
             (source->tree "[[1 2] [3 4]] [[5 6] [7 8]] +"))))

(describe "converting parse-trees to an internal representation"
  (it "handles numbers"))

(run-specs)
