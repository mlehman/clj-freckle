clj-freckle
===========
Clj-freckle is a light library for using the freckle API.

Use
---

(def my-credentials (struct credentials "my-token" "my-client-name"))

(get-users my-credentials)

(get-projects my-credentials)

(get-entries my-credentials)

(get-entries my-credentials {:to "iso-date-string" :from "iso-date-string"})
