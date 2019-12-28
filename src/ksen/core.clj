(ns ksen.core
  (:refer-clojure :exclude [read])
  (:import [java.io StringReader PushbackReader]))

(defrecord TwoDimensionPointReader [^PushbackReader reader x y])
(defprotocol ITwoDimensionPointReader
  (inc-x [this])
  (inc-y [this])
  (read [this])
  (unread [this c]))
(extend-protocol ITwoDimensionPointReader
  TwoDimensionPointReader
  (read [{:keys [reader x y]}]
    (let [res {:c (.read reader) :x @x :y @y}]
      (case (:c res)
        10 (swap! y inc)
        13 (swap! y inc)
        32 (swap! y inc)
        (swap! x inc))
      res))
  (unread [{:keys [reader x y]} c]
    (case c
      10 (swap! y dec)
      13 (swap! y dec)
      32 (swap! y dec)
      (swap! x dec))
    (.unread reader c)
    nil))
(defn- create-reader [s]
  (->TwoDimensionPointReader (PushbackReader. (StringReader. s))
                             (atom 0)
                             (atom 0)))

(defn- read-border-top [reader]
  (loop [buffer []]
    (let [{:keys [c x y]} (read reader)]
      (case c
        ;; 中央から右と下に伸びるやつ
        9484 (recur (conj buffer {:left x :top y}))
        ;; 横線
        9472 (recur buffer)
        ;; 中央から上以外に伸びるやつ
        9516 (recur (-> buffer
                        (update-in buffer [(- (count buffer) 1)]
                                   #(assoc % :width (- x (- (:left %) 1))))
                        (conj buffer {:left x :top y})))
        ;; 中央から左と下に伸びるやつ. これが来たら終わり.
        ;; FIXME: 行の最後まで読めてない. 同じ行に別の表が入ってたら壊れる.
        9488 (update-in buffer [(- (count buffer) 1)]
                        #(assoc % :width (- x (- (:left %) 1))))))))

(defn- read-body [reader]
  (loop [buffer []]
    (let [{:keys [c x y]} (read reader)]
      (case c
        ;; 縦線
        9474
        ;; TODO: まだ箱がなければ最初の箱を登録する
        ;; TODO: ...うーん、これでいいのか...？
        nil))))

(defn read-str
  [s]
  (let [reader (create-reader s)]
    (loop [result []]
      (let [{:keys [c x y]} (read reader)]
        (case c
          ;; 中央から右と下に伸びるやつ
          9484 (do (unread reader c)
                   (recur (concat result (read-border-top reader))))
          ;; 上から下のヤツ
          9474 (do (unread reader c)
                   ;; TODO: 同じx座標に端を持ち、かつheightが確定してないやつを探して、コンテンツを詰める
                   (read-body reader))
          ;; end of stream
          -1 result
          (recur result))))))
