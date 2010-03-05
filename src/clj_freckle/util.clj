(ns clj-freckle.util
  (:import (java.text SimpleDateFormat)
	   (java.util Calendar Date))
  (:use [clojure.contrib.zip-filter.xml :only [xml->]]
	[clojure.zip :only [xml-zip node]]))

;; Date Utilities

(def xsd-date-format (new SimpleDateFormat "yyyy-MM-dd'T'HH:mm:ss'Z'"))
(def iso-date-format (new SimpleDateFormat "yyyy-MM-dd"))

(defn parse-xsd-date [s]
  (.parse xsd-date-format s))

(defn parse-iso-date [s]
  (.parse iso-date-format s))

(defn after? [d1 d2]
  (< -1 (compare d1 d2)))

(defn before? [d1 d2]
  (= -1 (compare d1 d2)))

(defn in-range [date start end]
  (and (after? date start) (before? date end)))

(defn add-days [date n]
  (new Date 
    (..
      (doto (Calendar/getInstance)
        (.setTime date)
        (.add Calendar/DATE n)) getTime getTime)))

(defn minutes-to-hours [minutes-string]
  (float (/ (new Integer minutes-string) 60)))

;; xml utilities

(defn flatten-node [xml-node]
  (apply merge (map #(hash-map (% :tag) (first (% :content))) (xml-node :content))))

(defn flatten-xml [xml tag]
  (set (map #(flatten-node (node %)) 
	(xml-> (xml-zip xml) tag ))))

