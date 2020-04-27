--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cdr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cdr (
    cdr_id integer NOT NULL,
    dial_a integer,
    dial_b integer,
    service_id integer,
    duration_volume_msg integer,
    start_date date,
    start_time time without time zone,
    external_charges integer
);


ALTER TABLE public.cdr OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    customer_id integer NOT NULL,
    nid integer NOT NULL,
    customer_name text NOT NULL,
    msisdn integer NOT NULL,
    address text NOT NULL,
    email character varying(320) NOT NULL,
    profile_id integer
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: customer_freeunits; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer_freeunits (
    freeunit_id integer NOT NULL,
    customer_id integer NOT NULL,
    rest_units integer
);


ALTER TABLE public.customer_freeunits OWNER TO postgres;

--
-- Name: free_units; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.free_units (
    freeunit_id integer NOT NULL,
    quantity integer
);


ALTER TABLE public.free_units OWNER TO postgres;

--
-- Name: rate_plane; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rate_plane (
    profile_id integer NOT NULL,
    profile_name text,
    monthly_fees integer
);


ALTER TABLE public.rate_plane OWNER TO postgres;

--
-- Name: rating_package; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rating_package (
    service_id integer NOT NULL,
    freeunit_id integer NOT NULL,
    profile_id integer NOT NULL,
    desination_id integer NOT NULL,
    unitcost_id integer
);


ALTER TABLE public.rating_package OWNER TO postgres;

--
-- Name: service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.service (
    service_id integer NOT NULL,
    type text
);


ALTER TABLE public.service OWNER TO postgres;

--
-- Name: udr; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.udr (
    udr_id integer NOT NULL,
    dial_a integer,
    dial_b integer,
    service_id integer,
    duration_volume_msg integer,
    start_date date,
    start_time time without time zone,
    external_charges integer,
    profile_id integer,
    rating_charges integer
);


ALTER TABLE public.udr OWNER TO postgres;

--
-- Name: zone_package; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zone_package (
    operator_id integer NOT NULL,
    operator_name text,
    ndc integer
);


ALTER TABLE public.zone_package OWNER TO postgres;

--
-- Data for Name: cdr; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cdr (cdr_id, dial_a, dial_b, service_id, duration_volume_msg, start_date, start_time, external_charges) FROM stdin;
\.


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (customer_id, nid, customer_name, msisdn, address, email, profile_id) FROM stdin;
\.


--
-- Data for Name: customer_freeunits; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer_freeunits (freeunit_id, customer_id, rest_units) FROM stdin;
\.


--
-- Data for Name: free_units; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.free_units (freeunit_id, quantity) FROM stdin;
\.


--
-- Data for Name: rate_plane; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rate_plane (profile_id, profile_name, monthly_fees) FROM stdin;
\.


--
-- Data for Name: rating_package; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rating_package (service_id, freeunit_id, profile_id, desination_id, unitcost_id) FROM stdin;
\.


--
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.service (service_id, type) FROM stdin;
\.


--
-- Data for Name: udr; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.udr (udr_id, dial_a, dial_b, service_id, duration_volume_msg, start_date, start_time, external_charges, profile_id, rating_charges) FROM stdin;
\.


--
-- Data for Name: zone_package; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zone_package (operator_id, operator_name, ndc) FROM stdin;
\.


--
-- Name: cdr cdr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT cdr_pkey PRIMARY KEY (cdr_id);


--
-- Name: customer_freeunits customer_freeunits_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_freeunits
    ADD CONSTRAINT customer_freeunits_pkey PRIMARY KEY (freeunit_id, customer_id);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (customer_id);


--
-- Name: free_units free_units_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.free_units
    ADD CONSTRAINT free_units_pkey PRIMARY KEY (freeunit_id);


--
-- Name: rate_plane rate_plane_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rate_plane
    ADD CONSTRAINT rate_plane_pkey PRIMARY KEY (profile_id);


--
-- Name: rating_package rating_package_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating_package
    ADD CONSTRAINT rating_package_pkey PRIMARY KEY (service_id, profile_id, desination_id, freeunit_id);


--
-- Name: service service_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.service
    ADD CONSTRAINT service_pkey PRIMARY KEY (service_id);


--
-- Name: udr udr_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.udr
    ADD CONSTRAINT udr_pkey PRIMARY KEY (udr_id);


--
-- Name: zone_package zone_package_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zone_package
    ADD CONSTRAINT zone_package_pkey PRIMARY KEY (operator_id);


--
-- Name: customer_freeunits customer_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_freeunits
    ADD CONSTRAINT customer_id FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);


--
-- Name: rating_package desination_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating_package
    ADD CONSTRAINT desination_id FOREIGN KEY (desination_id) REFERENCES public.zone_package(operator_id);


--
-- Name: customer_freeunits freeunit_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer_freeunits
    ADD CONSTRAINT freeunit_id FOREIGN KEY (freeunit_id) REFERENCES public.free_units(freeunit_id);


--
-- Name: rating_package freeunit_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating_package
    ADD CONSTRAINT freeunit_id FOREIGN KEY (freeunit_id) REFERENCES public.free_units(freeunit_id);


--
-- Name: customer profile_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT profile_id FOREIGN KEY (profile_id) REFERENCES public.rate_plane(profile_id);


--
-- Name: rating_package profile_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating_package
    ADD CONSTRAINT profile_id FOREIGN KEY (profile_id) REFERENCES public.rate_plane(profile_id);


--
-- Name: udr profile_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.udr
    ADD CONSTRAINT profile_id FOREIGN KEY (profile_id) REFERENCES public.rate_plane(profile_id);


--
-- Name: rating_package service_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rating_package
    ADD CONSTRAINT service_id FOREIGN KEY (service_id) REFERENCES public.service(service_id);


--
-- Name: cdr service_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cdr
    ADD CONSTRAINT service_id FOREIGN KEY (service_id) REFERENCES public.service(service_id);


--
-- Name: udr service_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.udr
    ADD CONSTRAINT service_id FOREIGN KEY (service_id) REFERENCES public.service(service_id);


--
-- PostgreSQL database dump complete
--

