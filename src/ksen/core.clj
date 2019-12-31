(ns ksen.core
  (:import [java.io StringReader PushbackReader]))

(defn- index-of [f coll]
  (->> coll
       (map-indexed (fn [i c] [i c]))
       (filter #(f (second %)))
       first
       first))

(defn- find-right-x-of-box [m top]
  (index-of (fn [c]
              ;; (char 9488)
              ;; --+
              ;;   |
              ;; (char 9508)
              ;;   |
              ;; --+
              ;;   |
              (or (= (char 9488) c)
                  (= (char 9508) c)))
            (nth m top)))

(defn- find-bottom-y-of-box [m right]
  (index-of (fn [c]
              ;; (char 9496)
              ;;   |
              ;; --+
              ;; (char 9508)
              ;;   |
              ;; --+
              ;;   |
              (or (= (char 9496) c)
                  (= (char 9508) c)))
            (map #(nth % right) m)))

(defn- find-path-from-left-top [m x y]
  (let [right (find-right-x-of-box m y)
        bottom (find-bottom-y-of-box m right)]
    [[x y] [right y] [right bottom] [x bottom]]))

(defn- left-top [path]
  (->> path
       (sort-by (fn [[x y]] (+ x y)))
       first))

(defn- right-top [path]
  (->> path
       (sort-by (fn [[x y]] (- y x)))
       first))

(defn- right-bottom [path]
  (->> path
       (sort-by (fn [[x y]] (- 0 (+ x y))))
       first))

(defn- left-bottom [path]
  (->> path
       (sort-by (fn [[x y]] (- x y)))
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
        height (count m)
        get-by-point (fn [m x y] (-> m (nth x) (nth y)))]
    [{:path [[0 0] [width 0] [width height] [0 height]]
      :content (str (get-by-point m 1 1))}]
    (loop [result [] x 0 y 0]
      (let [c (-> m (nth y) (nth x))
            ;; (char 9484)
            ;;  +--
            ;;  |
            result (if (= c (char 9484))
                     (let [path (find-path-from-left-top m x y)
                           content (find-content m path)]
                       (conj result {:path path :content content}))
                     result)]
        (cond (and (= (+ x 1) width) (= (+ y 1) height)) result
              (< (+ x 1) width) (recur result (inc x) y)
              (and (= (+ x 1) width) (< (+ y 1) height)) (recur result 0 (inc y)))))))
