(ns majumble.core-spec
  (:require [speclj.core :refer :all]
            [majumble.core :refer :all]))

(describe "parsing"
  (it "parses number arguments"
    (should= [:S [:NS "10" "9"] [:OE [:O "+"] [:NS "8" "7"]]]
             (parse "10 9 + 8 7")))
  (it "parses matrix arguments"
      (should= [:S [:M [:NS "10" "9"]] [:OE [:O "+"] [:M [:NS "8" "7"]]]]
               (parse "[10 9] + [8 7]")))
      (it "parses 2-D matrix arguments"
          (should= [:S
                    [:M [:M [:NS "1" "2"]] [:M [:NS "3" "4"]]]
                    [:OE [:O "+"]
                     [:M [:M [:NS "5" "6"]] [:M [:NS "7" "8"]]]]]
                   (parse "[[1 2] [3 4]] + [[5 6] [7 8]]"))))

(run-specs)
