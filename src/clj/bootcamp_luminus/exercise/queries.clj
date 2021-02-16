(ns bootcamp-luminus.exercise.queries
  (:require [bootcamp-luminus.db.core :as db]
            [clojure.set :as set]))

(defn get-users! []
  (->> (db/sql-get-users)
       (map #(set/rename-keys % {:first_name :first-name
                                 :last_name :last-name}))))

(defn create-user! [{:keys [first-name last-name email pass]
                     :or {first-name nil
                          last-name nil
                          email nil
                          pass nil}}]
  (-> (db/sql-create-user! {:first_name first-name
                            :last_name last-name
                            :email email
                            :pass pass})
      first
      (set/rename-keys {:first_name :first-name
                        :last_name :last-name})))