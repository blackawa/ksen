(ns ksen.core)

(defn read-str
  [s]
  ;; TODO: 行を読み込むためのバッファを用意する
  ;; TODO: 区切り線が出てきた位置を記憶しつつ1行読む
  ;; TODO: 2行目は、区切り位置を見比べながらデータを格納していく
  ;; TODO: 横線が箱を閉じる形で表れたら、データを確定する。
  ;; 多分、確定前の一時バッファと確定したデータを貯めるバッファの2つが必要
  ;; clojure.data.jsonはloopでちょっとずつ結果を蓄積していってる
  ;; https://github.com/clojure/data.json/blob/master/src/main/clojure/clojure/data/json.clj#L36-L57
  ;; ここらへんで、codepointとそれが来た時の処理を生成してる
  (let [reader (java.io.StringReader. s)]
    (loop [result []
           x 0
           y 0]
      (let [c (.read reader)]
        ;; TODO: 閉じ角が来たらlengthを記録する。もし次のセルが始まるならそれも記録する
        (case c
          ;; 上から下のヤツ
          ;; TODO: まだ開いてる箱の端に一致してたら何もしない。箱の端に一致してなかったらエラーで落ちる
          9474 (recur result x y)
          ;; 中央から下、中央から右のヤツ
          9484 (recur (conj result {:position {:base {:x x :y y}}})
                      (inc x) y)
          ;; 中央から左、中央から下、中央から右のヤツ
          9516 (recur (conj result {:position {:base {:x x :y y}}}) x y) ;; 既存の箱のlengthを更新 and 次の箱を始める
          ;; 改行
          ;; TODO: 改行コードは他にもある
          10 (recur result x (inc y))
          ;; end of stream
          -1 result
          (recur result (inc x) y))))))
