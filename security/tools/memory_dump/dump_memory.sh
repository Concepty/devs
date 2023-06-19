#!/bin/bash

# Run the ps command and grep for the desired process
output=$(ps -ef | grep "process_name")

# Extract the PID from the output
pid=$(echo "$output" | awk '{print $2}')

# Check if the PID is not empty
if [[ -n $pid ]]; then
  # Run gcore with the extracted PID
  gcore "$pid"
else
  echo "Process not found."
fi