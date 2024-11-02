name: GitHub Classroom Workflow
on:
  push:
    branches:
      - master
  pull_request:
    branches:
      - master

jobs:
  build:
    name: Build
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
        with:
          fetch-depth: 0

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build
        run: mvn clean install

  test:
    name: Unit test
    runs-on: ubuntu-latest
    needs: build
    steps:
      - uses: actions/checkout@v3
        with:
          fetch-depth: 0

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Unit test
        run: mvn test

  deploy:
    runs-on: ubuntu-latest
    needs: [ build, test ]
    steps:
      - name: Check out the repository
        uses: actions/checkout@v3

      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Build with Maven
        run: mvn clean package

      - name: Deploy to Render
        run: |
          curl -X POST "https://api.render.com/v1/services/$RENDER_SERVICE_ID/deploys" \
            -H "Authorization: Bearer ${{ secrets.RENDER_API_KEY }}" \
            -H "Content-Type: application/json" \
            -d '{"clearCache": false}'

      - name: Run API Tests
        uses: matt-ball/newman-action@master
        with:
          collection: postman/[inst] Lab1.postman_collection.json
          environment: postman/[inst][heroku] Lab1.postman_environment.json
          delayRequest: 100
          reporters: '[ "cli" ]'
