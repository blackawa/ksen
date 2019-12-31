(ns ksen.core
  (:import [java.io StringReader PushbackReader])
  (:require [ksen.characters :as c]))

(defn- index-of [f coll]
  (->> coll
       (map-indexed (fn [i c] [i c]))
       (filter #(f (second %)))
       first
       first))

(defn- left-top-corner? [c]
  (->> #{c}
       (some (into #{} (concat c/left-top
                               c/left-middle
                               c/top-middle
                               c/middle-middle)))
       boolean))

(defn- find-right-x-of-box [m left top]
  (->> (subs (nth m top) (inc left))
       (index-of (fn [c]
                   (boolean (some (into #{} (concat c/right-top
                                                    c/right-middle
                                                    c/top-middle
                                                    c/middle-middle))
                                  #{c}))))
       (+ (inc left))))

(defn- find-bottom-y-of-box [m right top]
  (->> (subvec m (inc top))
       (map #(nth % right))
       (index-of (fn [c]
                   (boolean (some (into #{} (concat c/right-bottom
                                                    c/right-middle
                                                    c/bottom-middle
                                                    c/middle-middle))
                                  #{c}))))
       (+ (inc top))))

(defn- find-path-from-left-top [m x y]
  (let [right (find-right-x-of-box m x y)
        bottom (find-bottom-y-of-box m right y)]
    [[x y] [right y] [right bottom] [x bottom]]))

(defn- left-top [path]
  (->> path
       (sort-by (fn [[x y]] (+ x y)))
       first))

(defn- right-bottom [path]
  (->> path
       (sort-by (fn [[x y]] (- 0 (+ x y))))
       first))

(defn find-content [m path]
  (let [[left top right bottom] (concat (left-top path) (right-bottom path))]
    (->> (subvec m (inc top) bottom)
         (map (fn [s] (subs s (inc left) right)))
         (map (partial apply str))
         (clojure.string/join "\n"))))

(defn read-str [s]
  (let [m (->> (clojure.string/split s #"\n")
               (into []))
        width (count (first m))
        height (count m)]
    (loop [result [] x 0 y 0]
      (let [c (try (-> m (nth y) (nth x))
                   (catch java.lang.StringIndexOutOfBoundsException e
                     " "))
            result (if (left-top-corner? c)
                     (let [path (find-path-from-left-top m x y)
                           content (find-content m path)]
                       (conj result {:path path :content content}))
                     result)]
        (cond (and (= (+ x 1) width) (= (+ y 1) height)) result
              (< (+ x 1) width) (recur result (inc x) y)
              (and (= (+ x 1) width) (< (+ y 1) height)) (recur result 0 (inc y)))))))
