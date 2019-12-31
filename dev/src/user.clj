(ns user
  (:refer-clojure :exclude [test])
  (:require [clojure.test :refer :all]
            [clojure.tools.namespace.repl :refer [refresh set-refresh-dirs]]
            [eftest.runner :as eftest]))

;; workaround for https://github.com/clojure-emacs/cider/issues/2686
(set-refresh-dirs "dev/src" "src" "test")

(defn test
  ([]
   (test "test"))
  ([test-path]
   (eftest/run-tests (eftest/find-tests test-path))))
