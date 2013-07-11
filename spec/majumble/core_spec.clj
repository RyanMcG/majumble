(ns majumble.core-spec
  (:require [clojure.core.matrix :as ma]
            [speclj.core :refer :all]
            [majumble.core :refer :all]))

(describe "creating parse-trees"
  (it "parses without operators"
    (should= [:S [:NS "10" "9"]]
             (source->tree "10 9")))
  (it "parses number arguments"
    (should= [:S [:OE
                  [:AS
                   [:OE
                    [:AS [:NS "10" "9"]]
                    [:O "+"]]
                   [:NS "3"]]
                  [:O "*"]]]
             (source->tree "10 9 + 3 *")))
  (it "parses matrix arguments"
    (should= [:S
              [:OE
               [:AS
                [:OE
                 [:AS
                  [:M [:NS "10" "9"]] [:M [:NS "8" "7"]]]
                 [:O "+"]]
                [:M [:NS "3" "4"]]]
               [:O "x"]]]
             (source->tree "[10 9] [8 7] + [3 4] x")))
  (it "parses multi-dimensional matrix arguments"
    (should= [:S
              [:OE
               [:AS
                [:M [:M [:NS "1" "2"]] [:M [:NS "3" "4"]]]
                [:M [:M [:NS "5" "6"]] [:M [:NS "7" "8"]]]]
               [:O "+"]]]
             (source->tree "[[1 2] [3 4]] [[5 6] [7 8]] +"))))

(defmacro should-be-matrices=
  [expected-form actual-form]
  `(should= (map ma/matrix ~expected-form)
            ~actual-form))

(describe "converting parse-trees to an internal representation"
  (it "handles numbers"
    (should= '(10 9 8)
             (transform [:S [:NS "10" "9" "8"]])))
  (it "handles matrices"
    (should= '([10 9 8] [1 2])
             (transform [:S [:M [:NS "10" "9" "8"]] [:M [:NS "1" "2"]]]))
    (should= '([[1 2] [3 4]])
             (transform [:S [:M
                             [:M [:NS "1" "2"]]
                             [:M [:NS "3" "4"]]]])))
  (it "handles operators"
    (should= '(3) (transform [:S [:OE [:AS [:NS "1" "2"]] [:O "+"]]]))
    (should= '([3])
             (transform [:S [:OE
                             [:AS [:M [:NS "1"]] [:M [:NS "2"]]]
                             [:O "+"]]]))))

(describe "parsing source to internal representation"
  (it "transposes"
    (should= '([[1 2]
                [3 4]])
             (parse "[[1 2]
                      [3 4]]'"))))

(run-specs)
