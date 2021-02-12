(ns bootcamp-luminus.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[bootcamp-luminus started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[bootcamp-luminus has shut down successfully]=-"))
   :middleware identity})
