sudo docker run -t -d -v /sqlite/databases/stocksim.db:/app/stocksim.db -p 5004:5004 stocksim
echo Note if you need to update the databse you must go into the container and run dnx ef database update
echo Attach to the container using sudo docker exec -i -t cntrid bash
