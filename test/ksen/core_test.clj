(ns ksen.core-test
  (:require [clojure.test :refer :all]
            [ksen.core :as target]))

(deftest read-single-row
  (is (= [{:left 0 :top 1
           :height 3 :width 9
           :content " 1 2 3 "}]
         (target/read-str "
┌───────┐
│ 1 2 3 │
└───────┘
"))))
