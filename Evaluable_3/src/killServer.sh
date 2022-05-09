#!/bin/bash
kill -9 $(ps aux | grep rmiregistry | grep -v grep | awk '{print $2}')
