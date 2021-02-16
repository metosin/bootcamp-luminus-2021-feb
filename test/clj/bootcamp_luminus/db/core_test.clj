(ns bootcamp-luminus.db.core-test
  (:require
    [bootcamp-luminus.db.core :refer [*db*] :as db]
    [java-time.pre-java8]
    [luminus-migrations.core :as migrations]
    [clojure.test :refer :all]
    [next.jdbc :as jdbc]
    [bootcamp-luminus.config :refer [env]]
    [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'bootcamp-luminus.config/env
      #'bootcamp-luminus.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users
  (jdbc/with-transaction [t-conn *db* {:rollback-only true}]
    (let [new-user (first (db/sql-create-user!
                            t-conn
                            {:first_name "Sam"
                             :last_name "Smith"
                             :email "sam.smith@example.com"
                             :pass "pass"}
                            {}))]
      (is (some? new-user))
      (is (= (dissoc (db/sql-get-user t-conn {:id (:id new-user)} {}) :id)
             (dissoc new-user :id)
             {:first_name "Sam"
              :last_name "Smith"
              :email "sam.smith@example.com"})))))
