#!/bin/bash
# convert-database-url.sh
# Converts Render's DATABASE_URL to Spring Boot JDBC properties

if [ -z "$DATABASE_URL" ]; then
  echo "⚠️ DATABASE_URL not set, using defaults"
  export SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/ecommerce"
  export SPRING_DATASOURCE_USERNAME="postgres"
  export SPRING_DATASOURCE_PASSWORD="postgres"
else
  echo "🔗 Converting DATABASE_URL to JDBC format..."
  
  # Parse postgresql://user:password@host:port/database
  # Remove the 'postgresql://' prefix
  DB_URL_NO_PREFIX="${DATABASE_URL#postgresql://}"
  
  # Extract password and host part
  IFS='@' read -r CREDENTIALS HOST_AND_DB <<< "$DB_URL_NO_PREFIX"
  IFS=':' read -r USERNAME PASSWORD <<< "$CREDENTIALS"
  
  # Extract host, port, and database
  IFS='/' read -r HOST_PORT DB_NAME <<< "$HOST_AND_DB"
  IFS=':' read -r HOST PORT <<< "$HOST_PORT"
  
  # Default port to 5432 if not specified
  PORT="${PORT:-5432}"
  
  # Build JDBC URL
  export SPRING_DATASOURCE_URL="jdbc:postgresql://$HOST:$PORT/$DB_NAME"
  export SPRING_DATASOURCE_USERNAME="$USERNAME"
  export SPRING_DATASOURCE_PASSWORD="$PASSWORD"
  
  echo "✅ Database configuration:"
  echo "   Host: $HOST"
  echo "   Port: $PORT"
  echo "   Database: $DB_NAME"
  echo "   Username: $USERNAME"
  echo "   JDBC URL: $SPRING_DATASOURCE_URL"
fi

export SPRING_DATASOURCE_URL
export SPRING_DATASOURCE_USERNAME
export SPRING_DATASOURCE_PASSWORD
