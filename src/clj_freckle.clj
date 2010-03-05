(ns clj-freckle
  (:use [clojure.xml :only [parse]]
	[clojure.contrib.str-utils :only [str-join]] 
	[clj-freckle.util]))

(defstruct credentials :token :client)

(def resources 
     {:users     {:tag :user}
      :projects  {:tag :project}
      :entries   {:tag :entry}
      :tags   {:tag :tag}})

(defstruct search-options :people :projects :tags :from :to)

(defn- create-search-param [[map-key map-value]]
  (str "search["(name map-key) "]=" map-value))

(defn- create-search-params [options]
  (str-join "&" (map create-search-param options)))

(defn resource-url [{:keys [token client]} resource & [options]]
  (str "http://" client ".letsfreckle.com/api/" (name resource) ".xml?token=" token
       (if options
	 (str "&" (create-search-params options)))))

(defn- get-resource [credentials resource & [options]]
  (flatten-xml 
   (parse (resource-url credentials resource options))
   (-> resources resource :tag)))

(defn get-users [credentials]
    (get-resource credentials :users))

(defn get-projects [credentials]
    (get-resource credentials :projects))

(defn get-tags [credentials]
    (get-resource credentials :tags))

(defn get-entries [credentials & [options]] 
  (get-resource credentials :entries options))

(defn sum-hours [entries]
  (float (/ (reduce #(+ %1 (new Integer (%2 :minutes))) 0 entries) 60 )))