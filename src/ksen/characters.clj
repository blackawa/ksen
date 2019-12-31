(ns ksen.characters)

(defn- ->chars
  [coll] (map char coll))

(def left-top
  ;; +--
  ;; |
  (->> (concat (range 9484 9488) (range 9554 9557))
       ->chars))
(def right-top
  ;; --+
  ;;   |
  (->> (concat (range 9488 9492) (range 9557 9560))
       ->chars))
(def left-bottom
  ;; |
  ;; +--
  (->> (concat (range 9492 9496) (range 9560 9563))
       ->chars))
(def right-bottom
  ;;   |
  ;; --+
  (->> (concat (range 9496 9500) (range 9563 9566))
       ->chars))
(def left-middle
  ;; |
  ;; +--
  ;; |
  (->> (concat (range 9500 9508) (range 9566 9569))
       ->chars))
(def right-middle
  ;;   |
  ;; --+
  ;;   |
  (->> (concat (range 9508 9516) (range 9569 9572))
       ->chars))
(def top-middle
  ;; --+--
  ;;   |
  (->> (concat (range 9516 9524) (range 9572 9575))
       ->chars))
(def bottom-middle
  ;;   |
  ;; --+--
  (->> (concat (range 9524 9532) (range 9575 9578))
       ->chars))
(def middle-middle
  ;;   |
  ;; --+--
  ;;   |
  (->> (concat (range 9532 9548) (range 9578 9581))
       ->chars))
