(ns ksen.core
  (:import [java.io StringReader PushbackReader]))

(defn read-str
  [s]
  (let [m (->> (clojure.string/split s #"\n")
               (map (partial into [])))]
    [{:path [[0 0] [(- (count (first m)) 1) 0] [(- (count (first m)) 1) (- (count m) 1)] [0 (- (count m) 1)]]
      :content (->> m second second str)}]))
