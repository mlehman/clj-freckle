clj-freckle
===========
Clj-freckle is a light library for using the freckle API.

http://letsfreckle.com/help/beta/

The requests results are returned as a set of maps for easy use with clojure.set.

Use
---

(def my-credentials (struct credentials "my-token" "my-client-name"))

(get-users my-credentials)
(get-projects my-credentials)
(get-tags my-credentials)
(get-entries my-credentials)
(get-entries my-credentials {:to "iso-date-string" :from "iso-date-string"})

;using clojure.set
(project 
    (join 
        (get-projects my-credentials) 
        (get-entries my-credentials)
        {:id :project-id})
    [:date :minutes :name :description])
