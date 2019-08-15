(ns core
  (:require [datomic.client.api :as d]))

(comment
  (def cfg {
            :server-type :ion
            :region "eu-west-1"
            :system "personal-datomic"
            :endpoint "http://entry.personal-datomic.eu-west-1.datomic.net:8182"
            :proxy-port 8182})
  (def client (d/client cfg))
  (d/delete-database client {:db-name "movies"})
  (d/create-database client {:db-name "movies"})
  (def conn (d/connect client {:db-name "movies"}))
  (def movie-schema [
                      {:db/ident :movie/title
                       :db/valueType :db.type/string
                       :db/cardinality :db.cardinality/one
                       :db/doc "The title of the movie"}

                      {:db/ident :movie/genre
                       :db/valueType :db.type/string
                       :db/cardinality :db.cardinality/one
                       :db/doc "The genre of the movie"}

                      {:db/ident :movie/release-year
                       :db/valueType :db.type/long
                       :db/cardinality :db.cardinality/one
                       :db/doc "The year the movie was released in theaters"}]))

(comment
  (def first-movies [{:movie/title "The Goonies"
                      :movie/genre "action/adventure"
                      :movie/release-year 1985}
                     {:movie/title "Commando"
                      :movie/genre "thriller/action"
                      :movie/release-year 1985}
                     {:movie/title "Repo Man"
                      :movie/genre "punk dystopia"
                      :movie/release-year 1984}])
  (d/transact conn {:tx-data first-movies}))

(comment
  (d/transact conn {:tx-data movie-schema})
  (def db (d/db conn))
  (def all-titles-q '[:find ?movie-title
                      :where [_ :movie/title ?movie-title]]))

(comment
  (d/q all-titles-q db))

