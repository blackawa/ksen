(ns ksen.core-test
  (:require [clojure.test :refer :all]
            [ksen.core :as target]))

(deftest read-single-row
  (is (= [{:position {:base {:x 0 :y 1}
                      :height 3 :width 11}
           :content "1 2 3"}
          {:position {:base {:x 10 :y 1}
                      :height 3 :width 8}
           :content "45 6"}]
         (target/read-str "
┌─────────┬──────┐
│ 1 2 3   │ 45 6 │
└─────────┴──────┘
"))))
