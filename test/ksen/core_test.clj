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

(deftest read-multiple-rows-from-single-box
  (is (= [{:path [[0 0] [4 0] [4 5] [0 5]]
           :content "abc\ndef\nghi\njkl"}]
         (target/read-str "┌───┐
│abc│
│def│
│ghi│
│jkl│
└───┘
"))))

(deftest read-from-vertical-multiple-boxes
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

(deftest read-from-horizontal-multiple-boxes
  (is (= [{:path [[0 0] [4 0] [4 2] [0 2]]
           :content "abc"}
          {:path [[4 0] [8 0] [8 2] [4 2]]
           :content "def"}]
         (target/read-str "┌───┬───┐
│abc│def│
└───┴───┘
"))))

(deftest read-from-crossing-boxes
  (is (= [{:path [[0 0] [4 0] [4 2] [0 2]]
           :content "abc"}
          {:path [[4 0] [8 0] [8 2] [4 2]]
           :content "def"}
          {:path [[0 2] [4 2] [4 4] [0 4]]
           :content "ghi"}
          {:path [[4 2] [8 2] [8 4] [4 4]]
           :content "jkl"}]
         (target/read-str "┌───┬───┐
│abc│def│
├───┼───┤
│ghi│jkl│
└───┴───┘
"))))

(deftest read-from-horizontally-shifted-boxes
  (is (= [{:path [[0 0] [5 0] [5 2] [0 2]]
           :content "abcd"}
          {:path [[5 0] [8 0] [8 2] [5 2]]
           :content "ef"}
          {:path [[0 2] [3 2] [3 4] [0 4]]
           :content "gh"}
          {:path [[3 2] [8 2] [8 4] [3 4]]
           :content "ijkl"}]
         (target/read-str "┌────┬──┐
│abcd│ef│
├──┬─┴──┤
│gh│ijkl│
└──┴────┘
"))))
