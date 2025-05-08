# Installation Guide

## Prerequisites

- Docker and Docker Compose
- Git

## Installation Steps

1. Clone the repository:
```bash
git clone https://github.com/QuintinWeigand/Project_2_370_Team_1.git
cd Project_2_370_Team_1
```

2. Start the application using Docker Compose:
```bash
docker-compose up
```

The application will be available at http://localhost:8080

## Default Credentials

The application comes with a default admin account:
- Username: `admin`
- Password: `admin`

## Environment Variables

The following environment variables can be configured in docker-compose.yml:
- `MONGODB_HOST`: MongoDB host (default: mongodb)
- `MONGODB_PORT`: MongoDB port (default: 27017)

## Troubleshooting

If you encounter issues:
1. Make sure both Docker and Docker Compose are installed and running
2. Check if ports 8080 and 27017 are available on your system
3. Verify the MongoDB container is healthy before accessing the application
4. Check the application logs using `docker-compose logs app`
5. Check MongoDB logs using `docker-compose logs mongodb`