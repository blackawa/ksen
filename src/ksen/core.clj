(ns ksen.core
  (:import [java.io StringReader PushbackReader]))

;; note
;; (char 9484)
;;  +--
;;  |
;; (char 9488)
;; --+
;;   |
;; (char 9496)
;;   |
;; --+
;; (char 9492)
;; |
;; +--

(defn- index-of [f coll]
  (->> coll
       (map-indexed (fn [i c] [i c]))
       (filter #(f (second %)))
       first
       first))

;; 基点から罫線をつないでいき、元の座標に戻るまで調べる
(defn- find-path-from-left-top [m x y]
  ;; FIXME: 元の座標に戻らない場合は例外を送出して死なせる
  (letfn [(throw-box-broken-exception [c]
            (throw (ex-info "Broken box"
                            {:type ::broken-box
                             :searching-for c})))
          (check-broken [n c]
            (when (nil? n)
              (throw-box-broken-exception c)))]
    (let [right-top-x (index-of #(= (char 9488) %) (nth m y))
          _ (check-broken right-top-x (char 9488))
          right-bottom-y (index-of #(= (char 9496) %) (map #(nth % right-top-x) m))
          _ (check-broken right-bottom-y (char 9496))
          left-bottom-x (index-of #(= (char 9492) %) (nth m right-bottom-y))
          _ (check-broken left-bottom-x (char 9492))
          _ (when (not (= left-bottom-x x))
              (throw-box-broken-exception nil))]
      [[x y] [right-top-x y] [right-top-x right-bottom-y] [left-bottom-x right-bottom-y]])))

(defn find-content [m path]
  ;; TODO: 罫線がつながったら、そのpathの内部にあるコンテンツを全部抜き出す
  "1")

(defn read-str [s]
  (let [m (->> (clojure.string/split s #"\n")
               (map (partial into [])))
        width (count (first m))
        height (count m)
        get-by-point (fn [m x y] (-> m (nth x) (nth y)))]
    [{:path [[0 0] [width 0] [width height] [0 height]]
      :content (str (get-by-point m 1 1))}]
    (loop [result [] x 0 y 0]
      (let [c (-> m (nth y) (nth x))
            result (if (= c (char 9484))
                     (let [path (find-path-from-left-top m x y)
                           content (find-content m path)]
                       (conj result {:path path :content content}))
                     result)]
        (cond (and (= (+ x 1) width) (= (+ y 1) height)) result
              (< (+ x 1) width) (recur result (inc x) y)
              (and (= (+ x 1) width) (< (+ y 1) height)) (recur result 0 (inc y)))))))
