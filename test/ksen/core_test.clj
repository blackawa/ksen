(ns ksen.core-test
  (:require [clojure.test :refer :all]
            [ksen.core :as target]))

(deftest read-single-box
  (is (= [{:path [[0 0] [2 0] [2 2] [0 2]]
           :content "1"}]
         (target/read-str "┌─┐
│1│
└─┘
"))))

(deftest read-single-box-2
  (is (= [{:path [[0 0] [4 0] [4 2] [0 2]]
           :content "abc"}]
         (target/read-str "┌───┐
│abc│
└───┘
"))))
