(ns ksen.core-test
  (:require [clojure.test :refer :all]
            [ksen.core :as target]))

(deftest read-single-row
  (is (= [{:path [[0 0] [2 0] [2 2] [0 2]]
           :content "1"}]
         (target/read-str "┌─┐
│1│
└─┘
"))))
