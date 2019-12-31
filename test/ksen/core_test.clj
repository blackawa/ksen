(ns ksen.core-test
  (:require [clojure.test :refer :all]
            [ksen.core :as target]))

(deftest read-single-string-from-single-box
  (is (= [{:path [[0 0] [2 0] [2 2] [0 2]]
           :content "1"}]
         (target/read-str "┌─┐
│1│
└─┘
"))))

(deftest read-single-row-from-single-box
  (is (= [{:path [[0 0] [4 0] [4 2] [0 2]]
           :content "abc"}]
         (target/read-str "┌───┐
│abc│
└───┘
"))))

(deftest read-multiple-row-from-single-box
  (is (= [{:path [[0 0] [4 0] [4 5] [0 5]]
           :content "abc\ndef\nghi\njkl"}]
         (target/read-str "┌───┐
│abc│
│def│
│ghi│
│jkl│
└───┘
"))))

(deftest read-single-row-from-vertical-multiple-box
  (is (= [{:path [[0 0] [4 0] [4 2] [0 2]]
           :content "abc"}
          {:path [[0 2] [4 2] [4 4] [0 4]]
           :content "def"}]
         (target/read-str "┌───┐
│abc│
├───┤
│def│
└───┘
"))))
