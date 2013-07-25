(ns majumble.core-spec
  (:require [speclj.core :refer :all]
            [speclj.shoulds.matrix.core :refer [should-matrix==]]
            [majumble.core :refer :all]))

(describe "creating parse-trees"
  (it "parses without operators"
    (should= [:S [:N "10"] [:N "9"]]
             (source->tree "10 9")))
  (it "parses number arguments"
    (should= [:S [:OE
                  [:AS
                   [:OE
                    [:AS [:N "10"] [:N "9"]]
                    [:O "+"]]
                   [:N "3"]]
                  [:O "*"]]]
             (source->tree "10 9 + 3 *")))
  (it "parses matrix arguments"
    (should= [:S
              [:OE
               [:AS
                [:OE
                 [:AS
                  [:M [:N "10"] [:N "9"]] [:M [:N "8"] [:N "7"]]]
                 [:O "+"]]
                [:M [:N "3"] [:N "4"]]]
               [:O "x"]]]
             (source->tree "[10 9] [8 7] + [3 4] x")))
  (it "parses multi-dimensional matrix arguments"
    (should= [:S
              [:OE
               [:AS
                [:M [:M [:N "1"] [:N "2"]] [:M [:N "3"] [:N "4"]]]
                [:M [:M [:N "5"] [:N "6"]] [:M [:N "7"] [:N "8"]]]]
               [:O "+"]]]
             (source->tree "[[1 2] [3 4]] [[5 6] [7 8]] +"))))

(describe "converting parse-trees to an internal representation"
  (it "handles numbers"
    (should= '(10 9 8)
             (transform [:S [:N "10"] [:N "9"] [:N "8"]])))
  (it "handles matrices"
    (should-matrix== '([10 9 8] [1 2])
                     (transform [:S
                                 [:M [:N "10"] [:N "9"] [:N "8"]]
                                 [:M [:N "1"] [:N "2"]]]))
    (should-matrix== '([[1 2] [3 4]])
                     (transform [:S [:M
                                     [:M [:N "1"] [:N "2"]]
                                     [:M [:N "3"] [:N "4"]]]])))
  (it "handles operators"
    (should= '(3)
             (transform [:S [:OE [:AS [:N "1"] [:N "2"]] [:O "+"]]]))
    (should-matrix== '([3])
                     (transform [:S [:OE
                                     [:AS [:M [:N "1"]] [:M [:N "2"]]]
                                     [:O "+"]]]))))

(describe "parsing source to internal representation"
  (it "adds"
    (should-matrix== '([[5 5]
                        [5 5]])
                     (parse "[[1 2] [3 4]]
                             [[4 3] [2 1]] +")))
  (it "multiplication"
    (should-matrix== '(20)
                     (parse "[1 2 3 4]
                             [4 3 2 1] *"))
    (should-matrix== '([[7 10]
                        [15 22]])
                     (parse "[[1 2]
                              [3 4]]
                             [[1 2]
                              [3 4]] *")))
  (it "transposes"
    (should-matrix== '([[1 3]
                        [2 4]])
                     (parse "[[1 2] [3 4]]'"))))

(run-specs)
