#!/bin/sh
# convert-database-url.sh
# Converts Render's DATABASE_URL to Spring Boot JDBC properties

echo "========================================"
echo "=== DATABASE_URL Converter Started ==="
echo "========================================"
echo "PORT: ${PORT}"
echo "SPRING_PROFILES_ACTIVE: ${SPRING_PROFILES_ACTIVE}"
echo "DATABASE_URL present: $([ -z "$DATABASE_URL" ] && echo 'NO' || echo 'YES')"

SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/ecommerce"
SPRING_DATASOURCE_USERNAME="postgres"
SPRING_DATASOURCE_PASSWORD="postgres"

if [ -n "$DATABASE_URL" ]; then
  echo ""
  echo "=== Parsing DATABASE_URL ==="
  echo "INPUT: $DATABASE_URL"
  
  # Remove postgresql:// prefix
  URL_NO_PREFIX=$(echo "$DATABASE_URL" | sed 's|postgresql://||')
  
  # Extract user and password
  USER_PASS=$(echo "$URL_NO_PREFIX" | cut -d '@' -f 1)
  HOST_DB=$(echo "$URL_NO_PREFIX" | cut -d '@' -f 2)
  
  # Extract username and password
  USERNAME=$(echo "$USER_PASS" | cut -d ':' -f 1)
  PASSWORD=$(echo "$USER_PASS" | cut -d ':' -f 2-)
  
  # Extract host and port
  HOST_PORT=$(echo "$HOST_DB" | cut -d '/' -f 1)
  DB_NAME=$(echo "$HOST_DB" | cut -d '/' -f 2)
  
  # Split host and port
  if echo "$HOST_PORT" | grep -q ':'; then
    HOST=$(echo "$HOST_PORT" | cut -d ':' -f 1)
    PORT_DB=$(echo "$HOST_PORT" | cut -d ':' -f 2)
  else
    HOST="$HOST_PORT"
    PORT_DB="5432"
  fi
  
  # Build JDBC URL
  SPRING_DATASOURCE_URL="jdbc:postgresql://$HOST:$PORT_DB/$DB_NAME"
  SPRING_DATASOURCE_USERNAME="$USERNAME"
  SPRING_DATASOURCE_PASSWORD="$PASSWORD"
  
  echo "Database configuration:"
  echo "  Host: $HOST"
  echo "  Port: $PORT_DB"
  echo "  Database: $DB_NAME"
  echo "  Username: $USERNAME"
  echo "  JDBC URL: $SPRING_DATASOURCE_URL"
fi

# Export as environment variables so Spring Boot can pick them up
export SPRING_DATASOURCE_URL="$SPRING_DATASOURCE_URL"
export SPRING_DATASOURCE_USERNAME="$SPRING_DATASOURCE_USERNAME"
export SPRING_DATASOURCE_PASSWORD="$SPRING_DATASOURCE_PASSWORD"

echo ""
echo "=== Environment variables exported ==="
echo "SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL"
echo "SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME"
echo "SPRING_DATASOURCE_PASSWORD=(set)"
echo ""
echo "=== Starting Spring Boot application ==="
echo "========================================"
