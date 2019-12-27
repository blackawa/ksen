(ns ksen.core-test
  (:require [clojure.test :refer :all]
            [ksen.core :refer :all]))

(deftest foo-test
  (testing "foo"
    (is (= 0 (foo 0)))))
