(ns bootcamp-luminus.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [bootcamp-luminus.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[bootcamp-luminus started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[bootcamp-luminus has shut down successfully]=-"))
   :middleware wrap-dev})
